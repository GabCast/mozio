## Project ovwerview

The architecture for this project is MVVM plus repository pattern to manage data.
Networking provide the instance of retrofit client, in this case is a very simple client, but in the future it can be applied
interceptors, callbacks, adapters and so on.

## Coroutines - repository and view model

I decided to use coroutines for api calls because has a better manage for threading, with less line of codes to improve the
readability and scalability.
The coroutines is launch in the view model as is recommend by google, then the viewmodel create an instance of
the repository interface (this interface is to maintain repository unknown for the viewmodel) and inside de viewmodelsope call
for the repository suspend function.

The repository implements the service, make the request and wait for the response. As I mentioned before coroutines creates a
new thread so the UI/Main thread is not stopped, so the ui is allowed to do anything its required (show loading, go back, interact
with the view).

## Tests

I used mockk, mockito, robolectric, espresso and kluent for unit tests.

Mockk and kluent are both kotlin based libraries so we can use their power to make things easier.

Mockk provide better mock for things like objects (dont have to use power mock).
I used kluent instead of assertions because is more readable, you can use infix functions to achieve assertions.

