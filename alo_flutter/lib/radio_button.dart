import 'package:flutter/material.dart';

class MeuRadio extends StatefulWidget {
  @override
  State<MeuRadio> createState() => _RadioState();

}

class _RadioState extends State<MeuRadio> {

  int selecionado = -1;
  bool chk = false;

  @override
  Widget build(BuildContext context) {

    return Column(children:[
      Row(children: [
        Checkbox(
            value: chk,
            onChanged: (bool? val) {
              print ('selecionado: $val');
              setState(() {
                chk = val!;
              });
            }
        ),
        Text('Finalizar aula'),],),

      Row(children: [
        Radio(value: 0,
          groupValue: selecionado,
          onChanged: (int? item){
            setState((){
              this.selecionado = item!;
            });
          },

        ),
        Text('Abacate'),
      ],),

      Row(children:[
        Radio(value: -1,
          groupValue: selecionado,
          onChanged: (int? item){
            setState((){
              this.selecionado = item!;
            });
          },

        ),
        Text('Banana')
      ])

    ]);

  }

}