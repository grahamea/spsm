# SPMS

## Prerequisites 

* This Repo
* A Solace Pub Sub Monitor Release zip archive
* Docker (Engine) 
  * See [Docker](https://docs.docker.com/engine/) and 
  * [Install Docker Engine](https://docs.docker.com/engine/install/) 
* Docker Compose (docker-compose) 
  * See [Docker Compose](https://docs.docker.com/compose/) and 
  * [Install Docker Compose](https://docs.docker.com/compose/install/) 

## Setup

* make a copy of this repo e.g.
```shell
git clone <this repo>
git clone https://github.com/grahamea/spsm.git
```

* copy the Solace Pub Sub Monitor Release zip archive into the copy of the repo, e.g.
```shell
cp SolacePubSubMonitor_6.1.0.0.zip  ...spsm
```

* change directory to the copy of the repo .e.g.
```shell
cd ...spsm
```

* unpack (unzip) the Solace Pub Sub Monitor Release zip archive e.g. 
```shell
unzip -a SolacePubSubMonitor_6.1.0.0.zip 
```

* validate the unpacked SolacePubSubMonitor distribution
```shell
pushd SolacePubSubMonitor/rtvapm
 ./validate_install.sh
popd
```

## Staging 

This repo contains the neccesary files - pre staged to allow for one touch builds 

But instructions for manually (re) staging the required files are provided below 

### Staging files for the tomcat image build

Stage the neccesary file so that
We can build our own tomcat image (spms_tomcat) with the required changes so we can run 
an invariant tomcat instance in a container 

The files are the changes applied to a base tomcat image (config etc)
plus the configured war files for the deployed servlets

The changes are extracted from the Solace Pub Sub Monitor Release tomcat instance 
and placed in the appropriate staged/tomcat directories by the
staging/stage_tomcat.sh script e.g.

```shell
./staging/stage_tomcat.sh
```

The warfiles are built using the modified update_wars.sh file (staging/updatewars.sh)
that uses the haprimary and habackup host names and then copied to the staging/tomcat/warfiles directory 

### Staging files for the spsm volumes

We use (locally mounted) volumes to provide persistance to the spsm containers haprimary and habackup
So config changes will persist, and so that we can inject files used at runtime (e.g. database connector jars)

We need to create the volumes for mounting 
We also need to need to pre-populate the volumes as they will replace the contents of the mount point

This is done by the script staging/stage_volumes.sh e.g.

```shell
./staging/stage_volumes.sh
```



We need to stage the content of the 


## Building 
```shell
docker-compose build
```

## Running

```shell
docker-compose up -d
```

## Stopping 

```shell
docker-compose down
```

