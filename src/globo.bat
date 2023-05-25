@Echo off
cls
del %1.anl 2> null
java globoAL %1
if errorlevel 1 goto Fallo
java globoPRD %1
:Fallo