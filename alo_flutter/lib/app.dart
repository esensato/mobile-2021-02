import 'package:flutter/material.dart';

class App extends StatelessWidget {

  String msg = 'Boa noite!';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(title: 'Meu App',
                       debugShowCheckedModeBanner: false,
                       home: Scaffold(
                         appBar: AppBar(
                           title: Text('Titulo'),
                         ),
                         body: Center(child: Text(this.msg),),
                         bottomNavigationBar: BottomAppBar(child: Row(children: [
                           Text('Aqui embaixo!'),
                           Icon(Icons.adb_outlined)
                         ],)),
                         floatingActionButton: FloatingActionButton(
                           child: Icon(Icons.water_damage_outlined),
                           onPressed: () {
                             this.msg = 'Bom dia';
                             print('Clicou!!!!');
                           },
                         ),
                       ),
    );
  }

}