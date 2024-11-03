build jar: 
- `mvn clean package -P dev`
- `mvn clean -DskipTests -P dev`

build image:
- `docker build -t api-image .`

Run image -> container:
- `docker run -it -p 80:80 --name=api-container api-image`

- `docker compose build`
- `docker ps`
- `docker compose down`
- `docker compose logs -tf ....`
- `docker compose up`