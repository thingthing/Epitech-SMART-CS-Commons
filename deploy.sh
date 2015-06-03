#CS-Commons Deployement Script
mvn install

#Deploy Server after CS-Commons
cd ../server/
chmod +x deploy.sh
./deploy.sh