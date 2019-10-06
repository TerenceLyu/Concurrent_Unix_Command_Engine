# Concurrent_Unix_Command_Engine
An unix command engine that parse and excute unix command concurrently.
Implemented using Java Thread.
Supports following command:
* pwd
* ls
* cd
* cat
* grep
* wc
* uniq
* \> (pipeline operater)
* exit (quit the command line)

Additional command for concurrent operation:
* repl_jobs (provide a list of current active jobs with a id)
* kill (kill a active job with id)
