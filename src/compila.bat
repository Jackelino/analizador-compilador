@Echo off
cls
del %1.anl 2> null
java GloboAL %1
if errorlevel 1 goto Fallo
java GloboRRD %1
:Fallo