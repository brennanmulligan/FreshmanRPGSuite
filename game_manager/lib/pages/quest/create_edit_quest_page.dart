import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/quest/bloc/quest_bloc.dart';
import 'package:game_manager/repository/quest/objective_completion_type_DTO.dart';

import '../../repository/quest/action_type_DTO.dart';
import '../../repository/quest/quest_record.dart';
import '../../repository/quest/quest_editing_response.dart';
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

  // this is here for example purposes! take it out when you have the data
  // ready to fill in.
  static const List<String> list = <String>[
    'Quest Title',
    'Two',
    'Three',
    'Four'
  ];
  final List<Map<String, dynamic>> tempList = [
    {"name": "Janine", "age": 43, "role": "Professor"},
    {"name": "William", "age": 27, "role": "Associate Professor"},
    {"name": "John", "age": 40, "role": "Department Chair"},
    {"name": "Joanne", "age": 23, "role": "Professor"},
    {"name": "Alice", "age": 30, "role": "Adjunct Professor"},
    {"name": "Carol", "age": 38, "role": "Assistant Professor"},
    {"name": "Charles", "age": 31, "role": "Professor"},
    {"name": "Alex", "age": 45, "role": "AdjunctProfessor"},
    {"name": "Alicia", "age": 50, "role": "AdjunctProfessor"},
    {"name": "Bob", "age": 46, "role": "Professor"},
    {"name": "Larry", "age": 59, "role": "Professor"},
    {"name": "Terry", "age": 43, "role": "Professor"},
    {"name": "Arron", "age": 47, "role": "AssistantProfessor"},
    {"name": "Tyler", "age": 50, "role": "Professor"},
    {"name": "Charlie", "age": 60, "role": "President"},
  ];
  String dropdownValue = list.first;
  String customTextStyle = "Test";

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
    return MultiRepositoryProvider(
        providers: [
          RepositoryProvider(create: (context) => QuestRepository(dio: Dio()))
        ],
        child: BlocProvider<QuestBloc>(
            create: (context) =>
                QuestBloc(questRepository: context.read<QuestRepository>())
                  ..add(SendGetQuestEditingInformationEvent()),
            child: Scaffold(
                resizeToAvoidBottomInset: false,
                appBar: AppBar(
                  title: const Text('Create/Edit a Quest'),
                  backgroundColor: Colors.pink,
                ),
                body: BlocConsumer<QuestBloc, QuestState>(
                    listener: (context, state) {},
                    builder: (context, state) {
                      return BlocBuilder<QuestBloc, QuestState>(
                          builder: (context, state) {
                        if (state is QuestPageReady) {
                          return buildInputScreen(state.response);
                        } else {
                          return buildLoadScreen();
                        }
                      });
                    }))));
  }

  Widget buildLoadScreen() => const Padding(
      padding: EdgeInsets.all(24.0),
      child: Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ));

  Widget buildObjectivesTable() => SizedBox(
      height: 300,
      child: ListView(children: [
        DataTable(
          columns: const <DataColumn>[
            DataColumn(
              label: Expanded(
                child: Text(
                  'Name',
                  style: TextStyle(
                      fontWeight: FontWeight.bold,
                      decoration: TextDecoration.underline),
                ),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text(
                  'Age',
                  style: TextStyle(
                      fontWeight: FontWeight.bold,
                      decoration: TextDecoration.underline),
                ),
              ),
            ),
            DataColumn(
              label: Expanded(
                child: Text(
                  'Role',
                  style: TextStyle(
                      fontWeight: FontWeight.bold,
                      decoration: TextDecoration.underline),
                ),
              ),
            ),
          ],
          rows: List<DataRow>.generate(tempList.length, (index) {
            final name = tempList[index]["name"] ?? '';
            final age = tempList[index]["age"] ?? '';
            final role = tempList[index]["role"] ?? '';
            return DataRow(cells: <DataCell>[
              DataCell(Text(name)),
              DataCell(Text(age.toString())),
              DataCell(Text(role)),
            ]);
          }),
        ),
      ]));

  // INSTRUCTIONS/NOTES:
  // To make a new component of a page (another text field or dropdown), it
  // MUST be one of the children of the Column child.
  // The dropdown menu values will all show up as "Quest Title". When we have
  // actual data that will probably change to the hint as it's supposed to.
  // I have not added a button yet. That will require a buttonbuilder and its
  // own build context. look in create_player_page for reference.
  // you should put the submit button at the bottom of the buildinputscreen

  Widget buildInputScreen(questResponse) => Padding(
      padding: const EdgeInsets.all(24.0),
      child: Center(
          child: ListView(children: [
        // start putting in your page components here
        // QUEST TITLE
        Row(children: [
          Expanded(
            child: TextField(
              controller: addNewQuest,
              decoration: InputDecoration(
                label: Row(
                  children: const [
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
              items: questResponse.quests
                  .map<DropdownMenuItem<int>>((QuestRecord quests) {
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
              prefixIcon: Icon(Icons.map_outlined, color: Colors.pink)),
          hint: const Text("Trigger Map"),
          value: mapValue,
          isExpanded: true,
          onChanged: (String? value) {
            setState(() {
              mapValue = value!;
            });
          },
          items: questResponse.mapNames
              .map<DropdownMenuItem<String>>((String mapName) {
            return DropdownMenuItem<String>(
              value: mapName,
              child: Text(mapName),
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
        DropdownButtonFormField<int>(
          decoration: const InputDecoration(
              prefixIcon: Icon(Icons.incomplete_circle, color: Colors.pink)),
          hint: const Text("Completion Action Type"),
          value: actionValue,
          isExpanded: true,
          onChanged: (int? value) {
            setState(() {
              actionValue = value!;
            });
          },
          items: questResponse.completionActionTypes
              .map<DropdownMenuItem<int>>((ActionTypeDTO actions) {
            return DropdownMenuItem<int>(
              value: actions.actionID,
              child: Text(actions.actionName),
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
        ObjectiveWidget(completionTypes: []),
        buildObjectivesTable(),
        SubmitButtonBuilder(
          questId: questId ?? -1,
          questTitle:
              addNewQuest.text.isNotEmpty ? addNewQuest.text : questTitle,
          questDescription: questDesc.text.isNotEmpty ? questDesc.text : null,
          xpGained: experienceGained.text.isNotEmpty
              ? int.parse(experienceGained.text)
              : 0,
          triggerMapName: mapValue,
          triggerRow:
              triggerRow.text.isNotEmpty ? int.parse(triggerRow.text) : 0,
          triggerColumn:
              triggerColumn.text.isNotEmpty ? int.parse(triggerColumn.text) : 0,
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

class ObjectiveWidget extends StatefulWidget {
  ObjectiveWidget({
    Key? key,
    this.objectiveId = -1,
    this.questId = -1,
    this.objectiveDescription = '',
    this.experiencePointsGained = 0,
    this.completionType = const ObjectiveCompletionTypeDTO(
      id: -1,
      name: '',
    ),
    required this.completionTypes,
  }) : super(key: key);

  final int objectiveId;
  final int questId;
  String? objectiveDescription;
  int? experiencePointsGained;
  ObjectiveCompletionTypeDTO? completionType;
  final List<ObjectiveCompletionTypeDTO> completionTypes;

  //TODO: add way to just get the completiontypes

  @override
  State<StatefulWidget> createState() => _ObjectiveWidgetState();
}

class _ObjectiveWidgetState extends State<ObjectiveWidget> {

  @override
  Widget build(BuildContext context) {
    TextEditingController objectiveDescriptionController =
        TextEditingController();
    TextEditingController experiencePointsController = TextEditingController();
    int? completionTypeValue = widget.completionType!.id;

    return Container(
      margin: const EdgeInsets.all(10),
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.pink),
        borderRadius: BorderRadius.circular(10),
      ),
      child: Column(
        children: [
          // Row(
          //   mainAxisAlignment: MainAxisAlignment.end,
          //   children: [
          //     ElevatedButton(
          //       child: const Icon(Icons.delete),
          //       onPressed: () {}
          //     ),
          //   ],
          // ),
          Row(
            children: [
              const Icon(Icons.edit_note, color: Colors.pink),
              const SizedBox(
                width: 10,
              ),
              Text('Objective Description'),
              const Spacer(),
              ElevatedButton(
                child: const Icon(Icons.delete),
                onPressed: () {}
              ),
            ],
          ),
          TextField(
            controller: objectiveDescriptionController,
            decoration: InputDecoration(
              fillColor: Colors.grey,
            ),
          ),
          Row(
            children: [
              const Icon(Icons.exposure_plus_1, color: Colors.pink),
              const SizedBox(
                width: 10,
              ),
              Text('Experience Gained'),
            ],
          ),
          TextField(
            controller: experiencePointsController,
            decoration: InputDecoration(
              fillColor: Colors.grey,
            ),
          ),
          Row(
            children: [
              const Icon(Icons.incomplete_circle, color: Colors.pink),
              const SizedBox(
                width: 10,
              ),
              Text('Completion Type'),
            ],
          ),
          DropdownButtonFormField<ObjectiveCompletionTypeDTO>(
            decoration: const InputDecoration(
                prefixIcon: Icon(Icons.incomplete_circle, color: Colors.pink)),
            hint: const Text("Completion Type"),
            value: widget.completionType,
            isExpanded: true,
            onChanged: (ObjectiveCompletionTypeDTO? value) {
              setState(() {
                widget.completionType = value!;
              });
            },
            items: widget.completionTypes
                .map<DropdownMenuItem<ObjectiveCompletionTypeDTO>>(
                    (ObjectiveCompletionTypeDTO completionType) {
              return DropdownMenuItem<ObjectiveCompletionTypeDTO>(
                value: completionType,
                child: Text(completionType.name),
              );
            }).toList(),
          ),
        ],
      ),
    );
  }
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
