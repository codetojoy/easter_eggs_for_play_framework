
@Grapes(
      @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.5.14')
  )

import org.apache.http.client.methods.*
import org.apache.http.entity.*
import org.apache.http.impl.client.*

import java.util.concurrent.*

def pool = Executors.newFixedThreadPool(1)

def startApp = { ->
    Runnable runnableTask = () -> {
        try {
            def processBuilder = new ProcessBuilder()
            processBuilder.command("/bin/bash", "-c", "./launcher.sh")
            println "TRACER launch"
            def process = processBuilder.start()
            process.waitFor()
        } catch (Exception e) {
            println "TRACER start: caught ex"
        }
    }
    pool.execute(runnableTask)
}

def findApp = { ->
    def processBuilder = new ProcessBuilder()
    processBuilder.command("/bin/bash", "-c", "./find-app.sh")
    def process = processBuilder.start()
    int exitCode = process.waitFor()
    return exitCode == 0
}

def killApp = { ->
    def processBuilder = new ProcessBuilder()
    processBuilder.command("/bin/bash", "-c", "./kill-app.sh")
    def process = processBuilder.start()
    int exitCode = process.waitFor()
    return exitCode == 0
}

def pingApp = { ->
    def result = false
    def url = "http://localhost:9000"

    try {
        def request = new HttpGet(url)

        def client = HttpClientBuilder.create().build()
        def response = client.execute(request)

        def bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
        def text = bufferedReader.getText()
        println "TRACER ping response size: " + text.size()
        result = !text.isEmpty()
    } catch (Exception ex) {
        println "TRACER ping caught ex"
    }

    return result
}

def doSleep = { ->
    println "TRACER ..."
    try { Thread.sleep(10_000) } catch (Exception ex) {}
}

while (true) {
    def foundApp = findApp()

    if (foundApp) {
        def pingOk = pingApp()

        if (pingOk) {
            println "TRACER killing"
            killApp()
        }
    } else {
        println "TRACER starting app"
        startApp()
    } 

    doSleep()
}
