# Android.Media.Uploader

1) You must create like below link api.

        https://github.com/burakgumusfb/Android.Media.Uploader/wiki/File-Upload
        
        OR
        
        https://github.com/burakgumusfb/Android.Media.Uploader/wiki/Binary-Upload
        
2)  You must define Web Api IP address
    Common -> Constants - > REST_BASE_SERVICE = "http://192.168.1.190/";
    
3)  You must define Web Api method name in HttpModules -> IMedia
   
         Like -> : 
 
        public interface IMedia {

         @Multipart
         @POST("/api/values")
         void MediaUpload(@PartMap Map<String,TypedFile> media, Callback<String> response);
         
         @POST("/api/valuestwo")
         void MediaUploadByte(@Body FileData fileData, Callback<String> response);
        }

4) And run.
