
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("save-data")
    suspend fun saveData(
        @Body data: listadeprodutos
    ): Response<Void>
}
