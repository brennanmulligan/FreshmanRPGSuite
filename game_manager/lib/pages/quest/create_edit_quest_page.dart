import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/quest/bloc/quest_bloc.dart';
import 'package:game_manager/repository/quest/objective_completion_type_DTO.dart';

import '../../repository/quest/action_type_DTO.dart';
import '../../repository/quest/objective_record.dart';
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

  List<ObjectiveRecord> objectivesOnScreen = [];

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

  populate(QuestRecord quest) {
    experienceGained.text = quest.xpGained.toString();
    questDesc.text = quest.description;
    triggerRow.text = quest.triggerRow.toString();
    triggerColumn.text = quest.triggerCol.toString();
    fulfillmentObjectives.text = quest.objectivesForFulfillment.toString();
    startDate.text = quest.startDate.toString();
    endDate.text = quest.endDate.toString();
    mapValue = quest.triggerMapName;
    actionValue = quest.completionActionType.actionID;
    objectivesOnScreen = quest.objectives;
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

  Widget buildAddObjectiveButton(questResponse) => SizedBox(
    height: 100,
    child: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          IconButton(
            iconSize: 50,
            tooltip: 'Add a new objective',
            onPressed: (){
              setState(() {
                objectivesOnScreen.add(ObjectiveRecord(id: 0, description: '',
                    experiencePointsGained: 0, questID: 0, completionType: 0));
                buildObjectivesTable(questResponse.objCompletionTypes);
              });
            },
            icon: const Icon(Icons.add, color: Colors.pink),
          )
        ]
      )
    )
  );

  Widget buildLoadScreen() => const Padding(
      padding: EdgeInsets.all(24.0),
      child: Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ));

  Widget buildObjectivesTable(completionTypeList) => SizedBox(
      height: 300,
      child: ListView.builder(
        itemCount: objectivesOnScreen.length,
        itemBuilder: (BuildContext context, int index){
          ObjectiveRecord objective = objectivesOnScreen[index];
          return ObjectiveWidget(
            objectiveId: objective.id,
            questId: objective.questID,
            objectiveDescription: objective.description,
            experiencePointsGained: objective.experiencePointsGained,
            completionType: completionTypeList.firstWhere((obj) => obj.objCompletionId == objective.completionType),
            completionTypes: completionTypeList,
          );
        },
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
                  QuestRecord current  = questResponse.quests.firstWhere((quest) => quest.id == questId);
                  questTitle = current.title;
                  populate(current);
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
          decoration: InputDecoration(
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
          decoration: InputDecoration(
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
          decoration: InputDecoration(
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
              children: [
                Icon(Icons.calendar_today, color: Colors.pink),
                SizedBox(
                  width: 10,
                ),
                Text('Start Date (MM-dd-yyyy)'),
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
              children: [
                Icon(Icons.calendar_today, color: Colors.pink),
                SizedBox(
                  width: 10,
                ),
                Text('End Date (MM-dd-yyyy)'),
              ],
            ),
            fillColor: Colors.grey,
          ),
        ),
        buildObjectivesTable(questResponse.objCompletionTypes),
        buildAddObjectiveButton(questResponse),
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
      objCompletionId: -1,
      objCompletionName: '',
    ),
    required this.completionTypes,
  }) : super(key: key);

  final int objectiveId;
  final int questId;
  String objectiveDescription;
  int experiencePointsGained;
  ObjectiveCompletionTypeDTO? completionType;
  final List<ObjectiveCompletionTypeDTO> completionTypes;

  @override
  State<StatefulWidget> createState() => _ObjectiveWidgetState();
}

class _ObjectiveWidgetState extends State<ObjectiveWidget> {

  @override
  Widget build(BuildContext context) {
    TextEditingController objectiveDescriptionController =
        TextEditingController();
    TextEditingController experiencePointsController = TextEditingController();
    int? completionTypeValue = widget.completionType!.objCompletionId;

    objectiveDescriptionController.text = widget.objectiveDescription;
    experiencePointsController.text = widget.experiencePointsGained.toString();

    return Container(
      margin: const EdgeInsets.all(10),
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.pink),
        borderRadius: BorderRadius.circular(10),
      ),
      child: Column(
        children: [
          // Fields
          Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
            Expanded(
              child: TextField(
                controller: objectiveDescriptionController,
                decoration: InputDecoration(
                  label: Row(children: [
                    Icon(Icons.edit_note_rounded, color: Colors.pink),
                    SizedBox(
                      width: 10,
                    ),
                    Text('Description (Objective)'),
                  ]),
                  fillColor: Colors.grey,
                ),
              ),
            ),
            Expanded(
              child: TextField(
                controller: experiencePointsController,
                decoration: InputDecoration(
                  label: Row(children: [
                    Icon(Icons.exposure_plus_1_rounded, color: Colors.pink),
                    SizedBox(
                      width: 10,
                    ),
                    Text('Experience Points (Objective)'),
                  ]),
                  fillColor: Colors.grey,
                ),
              ),
            ),
            Expanded(
              child: DropdownButtonFormField<ObjectiveCompletionTypeDTO>(
                decoration: const InputDecoration(
                    prefixIcon:
                        Icon(Icons.incomplete_circle, color: Colors.pink)),
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
                    child: Text(completionType.objCompletionName),
                  );
                }).toList(),
              ),
            ),
            SizedBox(
                child: Row(children: [
              ElevatedButton(
                  child: const Icon(Icons.delete),
                  onPressed: () {
                      showAlertDialog(context, widget.objectiveId, widget.questId);
                  })
            ])),
          ])
        ],
      ),
    );
  }
}

// Confirmation box for deleting an objective
showAlertDialog(BuildContext context, objectiveId, questId) {
  // set up the buttons
  Widget continueButton = TextButton(
    child: const Text("Continue"),
    onPressed:  () { BlocProvider.of<QuestBloc>(context).add(
        DeleteObjectiveEvent(objectiveId, questId));
      Navigator.pop(context);
      },
  );
  Widget cancelButton = TextButton(
    child: const Text("Cancel"),
    onPressed:  () { Navigator.pop(context); },
  );
  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    content: const Text("Do you want to delete this objective?"),
    actions: [
      continueButton,
      cancelButton,
    ],
  );
  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
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
