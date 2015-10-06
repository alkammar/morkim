# morkim
This framework consists of 4 layers


											---------------------------------------------------
																	IO					Views/Persistence/
											---------------------------------------------------
																Presentation			Controllers/Gateways
											---------------------------------------------------
															  Use Case (Optional)
											---------------------------------------------------
															   Business Logic			Entities

Layers

1 - IO layer
2 - Presentation layer
3 - Business Logic layer
4 - Use case layer (Optional)

Abstractions

Application:
1 - MorkimApp - Extends the Android Application class 
creating the application concrete main parts/configurations (e.g. Controllers, Model, UseCase Factories, Repository, Analytics)
2 - AppContext - Provides abstract access to all the application concrete configurations.

MVP:
1 - View - Android Activities and Fragments abstraction to communicate with Controller.
2 - Controller (Controls flow form the view and presents/formats data form data model to view model.
3 - ViewModel - A hash map based data structure used by Controller to update View.
4 - Model - Data model container. This is where all your business models/entities live.
5 - Entity - Business model/entity.

Use Case: (Concerned with high level policies and leaves details to Model layer)
1 - UseCase - Executes a use case (a sequence of calls to Model/Entities APIs)
2 - Async use case - Executes a use case in a worker thread (AsyncTask).
3 - Sync use case - Executes a use case blocking until the use case completes.
4 - UseCaseRequest - Holds data to pass to the UseCase.
5 - UseCaseResponse - End result of a UseCase.

Repository:
1 - Repository - Allows access to repository Gateways.
2 - Gateway - Persists and retrieves Entity's data. Typically each Entity will have its own Gateway.