# Android.Media.Uploader

1) You must create like below code api.

     [HttpPost]
     public string Post()
        {
            try
            {
                var httpRequest = HttpContext.Current.Request;
                if (httpRequest.Files.Count > 0)
                {
                    for (int i = 0; i < httpRequest.Files.Count; i++)
                    {
                        var postedFile = httpRequest.Files[i];
                        var fileName = Path.GetFileName(postedFile.FileName);
                        var path = Path.Combine(System.Web.HttpContext.Current.Server.MapPath("~/Content/"), fileName);
                        postedFile.SaveAs(path);
                    }

                    return "Success";
                }
                return "Unsuccess";

            }
            catch (Exception ex)
            {

                return ex.Message + " " + ex.InnerException;
            }
        }
        
2)  You must define Web Api IP address
    Common -> Constants - > REST_BASE_SERVICE = "http://192.168.1.190/";
    
3)  You must define Web Api method name in HttpModules -> IMedia
   
 Like -> : 
 
 public interface IMedia {

    @Multipart
    @POST("/api/values")
    void MediaUpload(@PartMap Map<String,TypedFile> media, Callback<String> response);
}

4) And run..
