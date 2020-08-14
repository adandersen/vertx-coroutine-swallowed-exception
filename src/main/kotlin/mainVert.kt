import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.core.eventbus.requestAwait
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.kotlin.pgclient.pgConnectOptionsOf
import io.vertx.kotlin.sqlclient.executeAwait
import io.vertx.kotlin.sqlclient.getConnectionAwait
import io.vertx.kotlin.sqlclient.poolOptionsOf
import io.vertx.kotlin.sqlclient.prepareAwait
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class mainVert : AbstractVerticle() {

    companion object {
        lateinit var pool: PgPool
    }

    override fun start(startPromise: Promise<Void>) {
        val connectOptions = pgConnectOptionsOf(
            database = "exampledb",
            host = "localhost",
            port = 5432,
            connectTimeout = 29,
            idleTimeout = 3600,
            user = "postgres",
            password = "password"
        )

        pool = PgPool.pool(vertx, connectOptions, poolOptionsOf(maxSize = 6))

        vertx.eventBus().consumer<JsonObject>("a.b.c") {
            GlobalScope.launch(vertx.dispatcher()) {
                println("thread ${Thread.currentThread().name}")
                val connection = pool.getConnectionAwait()
                try {
                    val value = connection.prepareAwait("select * from test where id = $1;")
                        .query()
                        .executeAwait(Tuple.of("a string"))
                    it.reply(JsonObject().put("success", value))
                } catch (ex: Exception) {
                    it.reply(JsonObject().put("error", ex.message))
                }
            }
        }

        startPromise.complete()
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val vertx = Vertx.vertx()
    val deployId = vertx.deployVerticleAwait("mainVert")
    println("deployId: $deployId")
    val message = vertx.eventBus().requestAwait<JsonObject>("a.b.c", "message")
    println(message.body())
}