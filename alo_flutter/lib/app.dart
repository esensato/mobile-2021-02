import 'package:alo_flutter/item_menu.dart';
import 'package:flutter/material.dart';

class App extends StatefulWidget {

  @override
  State<App> createState() => _AppState();

}

class _AppState extends State<App> {

  String msg = 'Boa noite!';
  String hora = '12:00';
  int contador = 0;

   final listaItensMenu = [ItemMenu(titulo: "Foto", icone: Icon(Icons.camera_alt, color: Colors.blue,)),
     ItemMenu(titulo: "Sair", icone: Icon(Icons.transit_enterexit, color: Colors.blue,))];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(title: 'Meu App',
                       debugShowCheckedModeBanner: false,
                       home: Scaffold(
                         appBar: AppBar(
                           title: Text('Titulo'),
                           leading: PopupMenuButton<ItemMenu>(
                           itemBuilder: (BuildContext ctx) {
                             return listaItensMenu.map((e) {
                               return PopupMenuItem<ItemMenu>(child: Row(children: [
                                 e.icone,
                                 Text(e.titulo)
                               ],
                               )
                               );
                             }).toList();
                            },
                           )
                         ),
                         body: Center(child: Row(mainAxisAlignment: MainAxisAlignment.center,
                             children:[
                           IconButton(onPressed: (){
                             setState(() {
                               this.contador++;
                             });
                           }, icon: Icon(Icons.upload_sharp)),
                           Text('${this.contador}',
                             style: TextStyle(color: Colors.deepOrange, fontSize: 30)
                         ),
                           IconButton(onPressed: (){
                             setState(() {
                               this.contador--;
                             });
                           }, icon: Icon(Icons.download_sharp)),
                         ]),

                         ),
                         bottomNavigationBar: BotaoPersonalizado(),
                         floatingActionButton: FloatingActionButton(
                           child: Icon(Icons.water_damage_outlined),
                           onPressed: () {
                             setState(() {
                               this.hora = "15:00";
                               this.msg = 'Bom dia';
                             });
                             print('Clicou!!!!');
                           },
                         ),
                         persistentFooterButtons: [IconButton(onPressed: () {
                           print ('Clicou!');
                         }, icon: Icon(Icons.access_time))],
                       ),
    );
  }

}

class BotaoPersonalizado extends StatelessWidget {

  final String hora = '';

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(child: Row(children: [
      Text(this.hora),
      Icon(Icons.adb_outlined)
    ],));
  }
}