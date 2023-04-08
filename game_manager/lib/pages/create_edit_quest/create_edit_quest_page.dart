import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/get_majors_and_crews/bloc/get_majors_and_crews_bloc.dart';

import '../../repository/player/all_crews_response.dart';
import '../../repository/player/all_majors_response.dart';
import '../../repository/player/crew.dart';
import '../../repository/player/crews_repository.dart';
import '../../repository/player/major.dart';
import '../../repository/player/majors_repository.dart';
import '../../repository/player/player_repository.dart';
import '../../repository/player/basic_response.dart';
import '../shared/widgets/notification_card.dart';

class CreateEditQuestPage extends StatefulWidget {
  const CreateEditQuestPage({Key? key}) : super(key: key);

  @override
  State<CreateEditQuestPage> createState() => _CreateEditQuestPageState();


}

class _CreateEditQuestPageState extends State<CreateEditQuestPage>{
  final experienceGained = TextEditingController();
  final questDesc = TextEditingController();
  final triggerRow = TextEditingController();
  final triggerColumn = TextEditingController();
  final fulfillmentObjectives = TextEditingController();
  final startDate = TextEditingController();
  final endDate = TextEditingController();

  // this is here for example purposes! take it out when you have the data
  // ready to fill in.
  static const List<String> list = <String>['Quest Title', 'Two', 'Three',
    'Four'];
  String dropdownValue = list.first;

  int? questTitle;
  int? triggerMap;
  int? completionActionType;

  @override
  void initState() {
    super.initState();

    questTitle = 0;
    triggerMap = 0;
    completionActionType = 0;
  }


  // you'll probably have to change this when the time comes
  @override
  dispose() {
    super.dispose();

    experienceGained.dispose();
    triggerRow.dispose();
    triggerColumn.dispose();
    fulfillmentObjectives.dispose();
    startDate.dispose();
    endDate.dispose();

  }

  // DO NOT TAKE THIS OUT! the page will not build!
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text('Create/Edit a Quest'),
        backgroundColor: Colors.pink,
      ),
    body: buildInputScreen());
  }

  Widget buildLoadScreen() =>
      const Padding(
          padding: EdgeInsets.all(24.0),
          child: Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          )
      );

  // INSTRUCTIONS/NOTES:
  // To make a new component of a page (another text field or dropdown), it
  // MUST be one of the children of the Column child.
  // The dropdown menu values will all show up as "Quest Title". When we have
  // actual data that will probably change to the hint as it's supposed to.
  // I have not added a button yet. That will require a buttonbuilder and its
  // own build context. look in create_player_page for reference.
  // you should put the submit button at the bottom of the buildinputscreen

  Widget buildInputScreen() => Padding(
    padding: const EdgeInsets.all(24.0),
    child: Center(
      child: Column(
        children: [
          // start putting in your page components here
          // QUEST TITLE
          DropdownButtonFormField<String>(
            decoration: const InputDecoration(
              prefixIcon: Icon(Icons.assignment, color: Colors.pink)
            ),
            hint: const Text("Quest Title"),
            value: dropdownValue,
            isExpanded: true,
            onChanged: (String? value) {
              setState(() {
                dropdownValue = value!;
              });
            },
            items: list.map<DropdownMenuItem<String>>((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(value),
              );
            }).toList(),
          ),

          // QUEST DESCRIPTION:
          TextField(
            controller: questDesc,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.edit_note, color: Colors.pink),
                  SizedBox(
                    width: 10,
                    height: 10,
                  ),
                  Text('Quest Description...'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // EXPERIENCE GAINED
          TextField(
            controller: experienceGained,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.exposure_plus_1, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Experience Gained'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // TRIGGER MAP
          DropdownButtonFormField<String>(
            decoration: const InputDecoration(
                prefixIcon: Icon(Icons.map_outlined, color: Colors.pink)
            ),
            hint: const Text("Trigger Map"),
            value: dropdownValue,
            isExpanded: true,
            onChanged: (String? value) {
              setState(() {
                dropdownValue = value!;
              });
            },
            items: list.map<DropdownMenuItem<String>>((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(value),
              );
            }).toList(),
          ),

          // TRIGGER ROW
          TextField(
            controller: triggerRow,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.arrow_right_alt, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Trigger Row'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // TRIGGER COLUMN
          TextField(
            controller: triggerColumn,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.arrow_right_alt, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Trigger Column'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // FULFILLMENT OBJECTIVES
          TextField(
            controller: fulfillmentObjectives,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.edit_note, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Objectives for Fulfillment'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // COMPLETION ACTION TYPE
          DropdownButtonFormField<String>(
            decoration: const InputDecoration(
                prefixIcon: Icon(Icons.incomplete_circle, color: Colors.pink)
            ),
            hint: const Text("Trigger Map"),
            value: dropdownValue,
            isExpanded: true,
            onChanged: (String? value) {
              setState(() {
                dropdownValue = value!;
              });
            },
            items: list.map<DropdownMenuItem<String>>((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(value),
              );
            }).toList(),
          ),

          // START DATE
          TextField(
            controller: startDate,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.calendar_today, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Start Date'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

          // END DATE
          TextField(
            controller: endDate,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.calendar_today, color: Colors.pink),
                  SizedBox(
                    width: 10,
                  ),
                  Text('End Date'),
                ],
              ),
              fillColor: Colors.grey,
            ),
          ),

        ]
      )
    )
  );
}