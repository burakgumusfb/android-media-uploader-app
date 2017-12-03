package HttpModules;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit.mime.TypedFile;

/**
 * Created by BURAK on 12/2/2017.
 */

public interface IMedia {

    @Multipart
    @POST("/api/values")
    void MediaUpload(@PartMap Map<String,TypedFile> media, Callback<String> response);
}

