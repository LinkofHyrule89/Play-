#pragma once

template<typename StructType>
class COsStructManager
{
public:
	typedef uint32 iterator;

	COsStructManager(StructType* structBase, uint32 idBase, uint32 structMax)
	: m_structBase(structBase)
	, m_idBase(idBase)
	, m_structMax(structMax)
	{
		assert(m_idBase != 0);
	}

	StructType* GetBase() const
	{
		return m_structBase;
	}

	StructType* operator [](uint32 index) const
	{
		index -= m_idBase;
		if(index >= m_structMax)
		{
			return nullptr;
		}
		auto structPtr = m_structBase + index;
		if(!structPtr->isValid)
		{
			return nullptr;
		}
		return structPtr;
	}

	uint32 Allocate() const
	{
		for(unsigned int i = 0; i < m_structMax; i++)
		{
			if(!m_structBase[i].isValid) 
			{
				m_structBase[i].isValid = true;
				return (i + m_idBase);
			}
		}
		return -1;
	}

	void Free(uint32 id)
	{
		StructType* structPtr = (*this)[id];
		if(!structPtr->isValid)
		{
			throw std::exception();
		}
		structPtr->isValid = false;
	}

	void FreeAll()
	{
		for(unsigned int i = 0; i < m_structMax; i++)
		{
			m_structBase[i].isValid = false;
		}
	}

	iterator Begin() const
	{
		return m_idBase;
	}

	iterator End() const
	{
		return m_idBase + m_structMax;
	}

private:
	StructType*		m_structBase = nullptr;
	uint32			m_structMax = 0;
	uint32			m_idBase = 0;
};
