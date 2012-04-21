Sync Up Service
---------------------

# Introduction
Sync Up is an application to sync realtime presentations on embedded devices.

# Running the Service

* Fill in the database details in example.yml, it assumes that you have a MySQL database at your end

* To package the example run.

        mvn package

* To setup the MySQL database run.

        java -jar target/sync-up-0.1.0-SNAPSHOT.jar setup example.yml

* To run the server run.

        java -jar target/sync-up-0.1.0-SNAPSHOT.jar server example.yml