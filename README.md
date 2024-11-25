`Spring boot, hibernate(native query, jpa query, criteria query, specification query)`

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

Kill port:
- sudo kill -9 $(sudo lsof -t -i:5432)

// Send email

Step 1: Turn on 2 FA https://myaccount.google.com/signinoptions/two-step-verification/enroll-welcome

Step 2: Create app with password: https://myaccount.google.com/apppasswords

Step 3: Gan thong tin vao mail sender
```
spring.mail.username=xxx
spring.mail.password=xxx
```


