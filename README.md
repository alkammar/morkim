# morkim
An Android framework aiming to provide clean architectural base for applications as well as solving some known design issues in Android.
The framework leverages the use of SOLID principles, isolating your business logic from the Android platform and making it easy to write business unit tests.

## MVP front end 
Provides an MVP variation abstraction for front UI end modules. Basically you will need to extend Controller and Presenter classes to which handles synchronous and async callbacks to render data to the views correctly without caring about Activities and Fragments getting pushed to background or killed by the OS.

## Data repositories
Provide a data repository interfaces to isolate business logic from different data repository implementations. An example is ff you are in PoC and using Shared preferences for data storage and later wanted to migrate to database, this set of interfaces will protect your business logic from that change.

## Model container
A model container is used to hold your data model in memory, providing you with easy access to all your models added to it from any conntorller or background task.

## Background tasks
Wraps Android AsyncTask and Scheduler to provide a means for writing unit testable business use cases. The tasks are fully integrated with the MVP UI modules making it easy to synchronize UI thread with background threads.

