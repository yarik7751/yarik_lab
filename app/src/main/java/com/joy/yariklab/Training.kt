package com.joy.yariklab

fun main() {
    //println(map.get(1))
    // TestExpl.yarikMain()
    /*val cache = InMemoryCache<Int, String>()

    val scope = CoroutineScope(Job())

    val job1 = scope.launch {
        cache.subscribeOn(1)
            .collect {
                println(it)
            }
    }

    val job2 = scope.launch {
        cache.subscribeOn(2)
            .collect {
                // println(it)
            }
    }

    val job = scope.launch {
        coroutineScope {
            val deferred1 = async {
                withContext(Dispatchers.IO) {
                    cache.apply {
                        add(1, "Yarik")
                        add(1, "Vasya")
                        delay(100)
                        add(1, "Dima")
                        add(1, "Oleg")

                        delay(100)

                        remove(2, "Vova")
                        remove(2, "Zuzya")
                        removeByKey(2)
                    }
                }
            }

            val deferred2 = async {
                withContext(Dispatchers.IO) {
                    cache.apply {
                        add(2, "Rzora")
                        add(2, "Vova")

                        delay(50)

                        add(2, "Zuzya")
                        remove(1, "Yarik")
                    }
                }
            }

            deferred1.await()
            deferred2.await()
        }
    }

    runBlocking {
        job1.join()
        job2.join()

        job.join()
    }*/
}