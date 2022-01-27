# Loopiie-Internship
Android App Assignment for Loopiie LLP

Packages:
	
	api: Contains API methods and JSON response classes
	
	application: Cotains Application class and Hilt instance providers
	
	shared: Shared classes between the fragments
	
	ui: Fragments Classes
	
	utils: Helper and Utility Classes
	

Data Flow: MVVM Architecture followed

  Fragment -> ViewModel -> Repository -> PagingSource -> API -> fetched data bubbles up to the fragment to reflect UI changes


Libraries used:

1. Dagger Hilt for dependency injection (DI)
2. Glide to load images through url
3. Retrofit to make network calls 
4. Paging3 to paginate the fetched data
5. NavArgs / SafeArgs to pass data between fragments
