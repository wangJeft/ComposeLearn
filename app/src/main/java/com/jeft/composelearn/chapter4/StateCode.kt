package com.jeft.composelearn.chapter4

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeft.composelearn.ui.theme.Typography
import kotlinx.parcelize.Parcelize

//状态提升start
@Composable
fun CounterScreen() {
    var counter by remember {
        mutableStateOf(0)
    }
    CounterComponent(counter = counter, onIncrement = { counter++ }) {
        if (counter > 0) counter--
    }

}

/**
 * 状态自上向下, 事件自下向上
 */
@Composable
fun CounterComponent(counter: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = counter.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Typography.h3
        )
        Row {
            Button(onClick = { onDecrement() }, modifier = Modifier.weight(1f)) {
                Text(text = "-")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { onIncrement() }, modifier = Modifier.weight(1f)) {
                Text(text = "+")
            }
        }
    }
}
//状态提升end


//状态的持久化与恢复Start
@Parcelize
data class City(val name: String, val country: String) : Parcelable

//当第三方的类无法添加Parcelable接口时,可以通过自定义Saver的方式来使用
object CitySaver : Saver<City, Bundle> {
    override fun restore(value: Bundle): City? {
        return value.getString("name")?.let { name ->
            value.getString("country")?.let { country ->
                City(name, country)
            }
        }
    }

    override fun SaverScope.save(value: City): Bundle {
        return Bundle().apply {
            putString("name", value.name)
            putString("country", value.country)
        }
    }
}

/**
 * 使用rememberSaveable(原理是以bundle保存)可以将状态更长久的保存, 以在activity发生销毁重建时恢复数据
 */
@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }
    //自定义saver
    var selectedCity2 = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }

    //mapSaver
    val citySaver2 = run {
        val nameKey = "name"
        val countryKey = "country"
        mapSaver(save = { mapOf(nameKey to it.name, countryKey to it.country) },
            restore = { City(it[nameKey] as String, it[countryKey] as String) })
    }

    var selectedCity3 = rememberSaveable(stateSaver = citySaver2) {
        mutableStateOf(City("Madrid", "Spain"))
    }

    //listSaver
    val citySaver3 = listSaver<City, Any>(save = { listOf(it.name, it.country) },
        restore = { City(it[0] as String, it[1] as String) })
    var selectedCity4 = rememberSaveable(stateSaver = citySaver3) {
        mutableStateOf(City("Madrid", "Spain"))
    }

}
//状态的持久化与恢复End

//使用ViewModel管理状态
class CounterViewModel : ViewModel() {
    private val _counter = mutableStateOf(0)
    val counter by _counter
    fun increment() {
        _counter.value = _counter.value + 1
    }

    fun decrement() {
        if (_counter.value > 0) {
            _counter.value = _counter.value - 1
        }
    }
}


@Composable
fun CounterScreen2() {
    val viewModel: CounterViewModel = viewModel()
    CounterComponent(
        counter = viewModel.counter,
        onIncrement = viewModel::increment,
        onDecrement = viewModel::decrement
    )

}