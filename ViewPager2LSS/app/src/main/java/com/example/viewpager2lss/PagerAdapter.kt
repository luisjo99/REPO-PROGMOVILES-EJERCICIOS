import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager2lss.Fragmento1
import com.example.viewpager2lss.Fragmento2
import com.example.viewpager2lss.Fragmento3

class PagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> {
                Fragmento1()
            }
            1-> {
                Fragmento2()
            }
            2-> {
                Fragmento3()
            }
            else-> {throw Resources.NotFoundException("Position not found")}
        }
    }
}