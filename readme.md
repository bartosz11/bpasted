# bpasted

A simple pastebin made with [web7](https://github.com/bartosz11/web7) mainly made as a sample project. Utilizes Svelte and TailwindCSS for frontend. 
Not ready for production use yet.

## Running
If you really want to use this, I recommend running it inside Docker. <br>
Currently there's no prebuilt image, so you'll have to build it on your own. <br>
Clone this repo and just use ``docker build -t bartosz11/bpasted:latest .`` to build it. <br>
Next, create a volume where data will be stored. (ex. ``docker volume create pastebin-data`) <br>
Then you can use ``docker run -d --name pastebin -v pastebin-data:/home/container -p 4334:4334 bartosz11/bpasted:latest`` to run it. <br>
Alternatively, you can use docker-compose. [An example docker-compose.yml is available in this repository.](https://github.com/bartosz11/bpasted/blob/master/docker-compose.example.yml) <br>

Or, you can just run it as any other JAR file. JDK 17 is required to build one. <br>
Clone this repo, use ``./gradlew shadowJar`` . Your new jar will be out in `build/libs` directory. <br>
Then, you can just run it with `java -jar pastebin.jar`.
