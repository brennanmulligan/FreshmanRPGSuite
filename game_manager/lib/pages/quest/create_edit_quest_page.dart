import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/quest/bloc/quest_bloc.dart';
import 'package:game_manager/repository/quest/quest_editing_response.dart';

import '../../repository/quest/quest_record.dart';
import '../../repository/quest/quest_repository.dart';

class CreateEditQuestPage extends StatefulWidget {
  const CreateEditQuestPage({Key? key}) : super(key: key);

  @override
  State<CreateEditQuestPage> createState() => _CreateEditQuestPageState();
}

class _CreateEditQuestPageState extends State<CreateEditQuestPage> {
  final experienceGained = TextEditingController();
  final questDesc = TextEditingController();
  final triggerRow = TextEditingController();
  final triggerColumn = TextEditingController();
  final fulfillmentObjectives = TextEditingController();
  final startDate = TextEditingController();
  final endDate = TextEditingController();
  final addNewQuest = TextEditingController();
  late final questResponse;

  // this is here for example purposes! take it out when you have the data
  // ready to fill in.
  static const List<String> list = <String>[
    'Quest Title',
    'Two',
    'Three',
    'Four'
  ];
  String dropdownValue = list.first;

  int? questId;
  String? questTitle;
  String? mapValue;
  int? actionValue;

  @override
  void initState() {
    super.initState();
    addNewQuest.addListener(refresh);
  }

  // you'll probably have to change this when the time comes
  @override
  dispose() {
    super.dispose();
    addNewQuest.dispose();
    experienceGained.dispose();
    triggerRow.dispose();
    triggerColumn.dispose();
    fulfillmentObjectives.dispose();
    startDate.dispose();
    endDate.dispose();
    addNewQuest.removeListener(refresh);
  }

  // Function to sync the password visibilities by refreshing Parent state
  refresh() {
    setState(() {});
  }

  // DO NOT TAKE THIS OUT! the page will not build!
  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(providers: [
      RepositoryProvider(create: (context) => QuestRepository(dio: Dio()))
    ],
        child: BlocProvider<QuestBloc>(
          create: (context) => QuestBloc(
            questRepository: context.read<QuestRepository>())..add(
              SendGetQuestEditingInformationEvent()),
          child: Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: AppBar(
              title: const Text('Create/Edit a Quest'),
              backgroundColor: Colors.pink,
            ),
            body: BlocConsumer<QuestBloc, QuestState>(
              listener: (context, state){},
              builder: (context, state){
                return BlocBuilder<QuestBloc, QuestState>(builder: (context,
                state) {
                  if(state is QuestPageReady) {
                    questResponse = state.response;
                    print(questResponse);
                    return buildInputScreen(questResponse);
                  }
                  else {
                    return const Center(child: CircularProgressIndicator());
                  }
                });
              }
            )
          )
        ));
  }

  Widget buildLoadScreen() => const Padding(
      padding: EdgeInsets.all(24.0),
      child: Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ));

  // INSTRUCTIONS/NOTES:
  // To make a new component of a page (another text field or dropdown), it
  // MUST be one of the children of the Column child.
  // The dropdown menu values will all show up as "Quest Title". When we have
  // actual data that will probably change to the hint as it's supposed to.
  // I have not added a button yet. That will require a buttonbuilder and its
  // own build context. look in create_player_page for reference.
  // you should put the submit button at the bottom of the buildinputscreen

  Widget buildInputScreen(QuestResponse questResponse) => Padding(
      padding: const EdgeInsets.all(24.0),
      child: Center(
          child: ListView(children: [
        // start putting in your page components here
        // QUEST TITLE
        Row(children: [
          Expanded(
            child: TextField(
              controller: addNewQuest,
              decoration: const InputDecoration(
                label: Row(
                  children: [
                    Icon(Icons.edit_note, color: Colors.pink),
                    SizedBox(
                      width: 10,
                      height: 10,
                    ),
                    Text('Type New Quest Name:'),
                  ],
                ),
                fillColor: Colors.grey,
              ),
            ),
          ),
          const SizedBox(width: 10),
          Expanded(
            child: DropdownButtonFormField<int>(
              decoration: const InputDecoration(
                  prefixIcon: Icon(Icons.assignment, color: Colors.pink)),
              hint: const Text("Quest Title"),
              value: questId,
              isExpanded: true,
              onChanged: (int? value) {
                setState(() {
                  questId = value!;
                });
              },
              items: questResponse.questEditingInfoDTO.quests.map<DropdownMenuItem<int>>((QuestRecord quests) {
                return DropdownMenuItem<int>(
                  value: quests.id,
                  child: Text(quests.title),
                );
              }).toList(),
            ),
          )
        ]),
        // QUEST DESCRIPTION:
        TextField(
          controller: questDesc,
          decoration: const InputDecoration(
            label: Row(
              children: [
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
              prefixIcon: Icon(Icons.map_outlined, color: Colors.pink)),
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
              prefixIcon: Icon(Icons.incomplete_circle, color: Colors.pink)),
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
          decoration: const InputDecoration(
            label: Row(
              children: [
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
        SubmitButtonBuilder(
          questId: questId ?? -1,
          questTitle: addNewQuest.text.isNotEmpty
              ? addNewQuest.text
              : questTitle,
          questDescription:
              questDesc.text.isNotEmpty ? questDesc.text : null,
          xpGained: experienceGained.text.isNotEmpty
              ? int.parse(experienceGained.text)
              : 0,
          triggerMapName: mapValue,
          triggerRow:
              triggerRow.text.isNotEmpty ? int.parse(triggerRow.text) : 0,
          triggerColumn: triggerColumn.text.isNotEmpty
              ? int.parse(triggerColumn.text)
              : 0,
          fulfillmentObjectives: fulfillmentObjectives.text.isNotEmpty
              ? int.parse(fulfillmentObjectives.text)
              : 0,
          completionActionType: actionValue ?? 0,
          startDate: startDate.text.isNotEmpty ? startDate.text : null,
          endDate: endDate.text.isNotEmpty ? endDate.text : null,
          isEasterEgg: false,
          isValid: questTitle != null || addNewQuest.text.isNotEmpty,
        )
      ])));
}

class SubmitButtonBuilder extends StatelessWidget {
  SubmitButtonBuilder({
    Key? key,
    this.questId = -1,
    required this.questTitle,
    this.questDescription,
    this.xpGained = 0,
    this.triggerMapName,
    this.triggerRow = 0,
    this.triggerColumn = 0,
    this.fulfillmentObjectives = 0,
    this.completionActionType = 0,
    this.startDate,
    this.endDate,
    this.isEasterEgg = false,
    required this.isValid,
  }) : super(key: key);

  final int questId;
  final String? questTitle;
  final String? questDescription;
  final int xpGained;
  final String? triggerMapName;
  final int triggerRow;
  final int triggerColumn;
  final int fulfillmentObjectives;
  final int completionActionType;
  final String? startDate;
  final String? endDate;
  final bool isEasterEgg;

  bool isValid;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          foregroundColor: Colors.blue,
          minimumSize: const Size(double.infinity, 50),
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(1)),
          ),
        ),
        onPressed: !isValid
            ? null
            : () => BlocProvider.of<QuestBloc>(context).add(
                SendUpsertQuestEvent(
                    questId,
                    questTitle,
                    questDescription,
                    xpGained,
                    triggerMapName,
                    triggerRow,
                    triggerColumn,
                    fulfillmentObjectives,
                    completionActionType,
                    startDate,
                    endDate,
                    isEasterEgg)),
        child: Text(
          (questId == -1) ? "Create Quest" : "Update Quest",
          style: const TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
