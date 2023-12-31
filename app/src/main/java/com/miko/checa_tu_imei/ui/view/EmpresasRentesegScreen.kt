package com.miko.checa_tu_imei.ui.view


import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import com.miko.checa_tu_imei.ui.view.components.CustomDrawerContent
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.launch


data class Empresas(val id: String, val nombre: String,val pagina_web: String)
@Composable
fun EmpresasRentesegScreen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    EmpresasRenteseg(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpresasRenteseg(
    //Parámetro NavHostController
    navHostController: NavHostController,
    //Parametro Switch
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    //Variables para el drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItemDrawer =2

    //VARIABLES PARA CARGAS LOS DATOS DE LA API DE EMPRESAS
    viewModel.loadEmpresas()
    val empresasRenteseg by viewModel.empresas.observeAsState(initial = emptyList())
    val itemsEmpresaRenteseg = empresasRenteseg.map { Empresas(it.id,it.nombre,it.pagina_web) }



    MaterialTheme() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CustomDrawerContent(
                        isDarkTheme = isDarkTheme,
                        selectedItem = selectedItemDrawer,
                        drawerState = drawerState,
                        navHostController = navHostController,
                        icon = icon,
                        )
                },
                content = {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        stringResource(R.string.drawer_empresas_renteseg),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { navHostController.navigate(AppScreens.PreguntasFrecuentesScreen.route) }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                }
                            )
                        },
                        content = { innerPadding ->
                            Column(
                                modifier = Modifier.padding(innerPadding),

                                ) {
                                ElevatedCard(modifier= Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),

                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                        //SEARCH
                                        AutoCompleteTextField(items = itemsEmpresaRenteseg)
                                        //
                                        Row() {
                                            ElevatedButton(
                                                modifier = Modifier.weight(1f),
                                                onClick = { navHostController.popBackStack() },
                                                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowBack,
                                                    contentDescription = "Favorite Icon",
                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                )
                                                Text(
                                                    modifier = Modifier.padding(start = 10.dp),
                                                    text = stringResource(R.string.regresar),
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = TextStyle(fontSize = 18.sp)
                                                )
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(items: List<Empresas>) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            modifier=Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { newQuery -> query = newQuery },
            label = { Text("Buscar") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            val results = items.filter { it.nombre.contains(query, ignoreCase = true) }
            items(results) { item ->
                if (item.id != "0"){
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(vertical = 4.dp ).clickable { query = item.nombre }
                        //Modifier.fillMaxWidth(),
                        //Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        CardGeneral(item.nombre,item.pagina_web)
                    }
                }

                /*
                Text(
                    text = "${item.nombre} - ${item.pagina_web}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { query = item.nombre }
                        .padding(8.dp)
                )

                 */

            }
        }
    }
}


@Composable
private fun CardGeneral(
    pregunta: String,
    respuesta: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(pregunta,respuesta)
    }
}

@Composable
private fun CardContent(pregunta: String,respuesta: String) {

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            //Text(text = "Hello, ")
            Text(
                text = pregunta,
                /*
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),

                 */
                style = TextStyle(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
            )
            if (expanded) {
                Text(
                    text = (respuesta),
                )
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                //imageVector = if (expanded) Icons.Filled.ArrowBack else Icons.Filled.ArrowDropDown,
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }

    }
}



