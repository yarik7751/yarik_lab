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
                        addName(1, "Yarik")
                        addName(1, "Vasya")
                        delay(100)
                        addName(1, "Dima")
                        addName(1, "Oleg")

                        delay(100)

                        removeName(2, "Vova")
                        removeName(2, "Zuzya")
                        remove(2)
                    }
                }
            }

            val deferred2 = async {
                withContext(Dispatchers.IO) {
                    cache.apply {
                        addName(2, "Rzora")
                        addName(2, "Vova")

                        delay(50)

                        addName(2, "Zuzya")
                        removeName(1, "Yarik")
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