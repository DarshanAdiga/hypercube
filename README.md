Hypercube
=======================
This is framework to build simple master-worker styled distributed applications.

WARNING:
========
THIS PROJECT IS ONLY EXPERIMENTAL

Run the master
---------------
```bash
java -cp hypercube-master.jar org.adiga.hypercube.master.MasterActor
```

Run the workers
---------------
```bash
#Assuming jar is built as worker main class
java -cp hypercube-worker.jar org.adiga.hypercube.worker.WorkerActor 2626
```