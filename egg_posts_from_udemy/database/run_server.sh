# docker run -d --name my-standalone-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=swordfish mysql:5.7.41-debian --default-authentication-plugin=mysql_native_password

docker run -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD="swordfish" --name my-pg postgres:latest
