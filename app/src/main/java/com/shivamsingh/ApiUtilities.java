package com.shivamsingh;

import androidx.multidex.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiUtilities {

    // Retrofit instance to ensure only one instance is created (Singleton pattern)
    private static Retrofit retrofit = null;

    /**
     * Provides the ApiInterface implementation.
     * Initializes Retrofit if it hasn't been initialized yet.
     */
    public static ApiInterface getApiInterface() {
        if (retrofit == null) {

            // Create a logging interceptor for debugging HTTP requests and responses
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

            // Enable detailed HTTP logs only when the app is in debug mode
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // Log request and response bodies
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);  // Disable logging in production
            }

            // Configure OkHttpClient with custom timeout settings and attach the logging interceptor
            // Build OkHttpClient with custom settings:
            // - Set connection timeout (how long to wait when connecting)
            // - Set read timeout (how long to wait to receive data)
            // - Set write timeout (how long to wait to send data)
            // - Add logging interceptor to log network calls

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  // Wait max 30 seconds to establish connection to server
                    .readTimeout(30, TimeUnit.SECONDS)     // Wait max 30 seconds to receive data from server
                    .writeTimeout(30, TimeUnit.SECONDS)    // Wait max 30 seconds to send data to server
                    .addInterceptor(loggingInterceptor)    // Attach logging interceptor to log network calls
                    .build();

            // Create Gson instance for JSON serialization/deserialization
            // The call to excludeFieldsWithoutExposeAnnotation() tells Gson to
            // serialize/deserialize **only** fields annotated with @Expose
            // This helps control which fields are included in JSON operations
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            // Build Retrofit instance configured with:
            // - Base URL for all API requests
            // - Gson converter to handle JSON to Java object mapping
            // - Custom OkHttpClient with timeout and logging settings
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        // Create and return an implementation of ApiInterface
        return retrofit.create(ApiInterface.class);
    }
}

/*
 When you call .excludeFieldsWithoutExposeAnnotation(), Gson ignores any fields that do NOT have the @Expose annotation.
 This is useful for controlling which fields you want included in your JSON parsing, especially if your model class has some fields you don’t want to send or receive.
*/



/*
package com.shivamsingh;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    public static Retrofit retrofit=null;

    public static ApiInterface getApiInterface(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
*/
