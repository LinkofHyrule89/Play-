#include <windows.h>
#include "PsfVm.h"
#include "TextDebugger.h"

using namespace std;

//int main(int argc, char** argv)
int WINAPI WinMain(HINSTANCE instance, HINSTANCE, char*, int)
{
    AllocConsole();
    string filename = "C:\\nsf\\kh\\Kingdom Hearts (Sequences Only)\\101 - Dearly Beloved.psf2";
//    string filename = "C:\\nsf\\FF10\\111 Game Over.minipsf2";
    CPsfVm virtualMachine(filename.c_str());

    CTextDebugger debugger(virtualMachine.GetCpu());
    debugger.Run();
    return 1;
}