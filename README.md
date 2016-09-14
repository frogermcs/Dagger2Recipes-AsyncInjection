# Dagger2Recipes: AsyncInjection
Example app which shows how to make async injection with RxJava in Dagger 2

---

We all now that every 100ms matters in application launch time. Users don't want to see splash screens anymore. But unfortunatelly sometimes libs authors forget about initialisation time profilling and 3rd parties setup takes too much time. One of the solutions can be asynchronous initialisation with RxJava and Dagger 2. 

This recipe shows how to do the simples implementation and shows the difference between blocking and asynchronous injection.

Check blog post: [Async Injection in Dagger 2 with RxJava](http://frogermcs.github.io/async-injection-in-dagger-2-with-rxjava/) for more details.
