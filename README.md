# aw10-final

项目本身采用WebFlux进行响应式编程，实现了products，carts，orders服务，
由于使用了Spring-Cloud Discovery，扩展只需要多开几个服务的实例即可。

Please develop a **fully functional** online purchase order system.

- It should have a superb collection of goods merchandises
- Customer can browse/search for merchandises, add selected one into his shopping cart and checkout to complete a transaction.
- User can get delivery status updates continuously.

The system should be of a **reactive architecture**, which means it should be

- Responsive: it should response to the user request timely.
- Resilient: it should not be easily broken down.
- Elastic: it should be flexible to scale out.
- Message Driven: it should has loosely coupled components that communicates with each other asynchronously.

Please design tests/experiements to demostrate that your system fulfills such requirements as stated in [The Reactive Manifesto](https://www.reactivemanifesto.org)
