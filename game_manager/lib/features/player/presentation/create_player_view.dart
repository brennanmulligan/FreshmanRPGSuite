import 'package:flutter/material.dart';
import 'package:game_manager/features/player/player.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class CreatePLayerView extends HookConsumerWidget {
  const CreatePLayerView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final username = TextEditingController();
    final password = TextEditingController();
    final crew = TextEditingController();
    final major = TextEditingController();
    final section = TextEditingController();

    final notifier = ref.watch(
      PlayerProvider.playerController.notifier,
    );

    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text('Create Player'),
        centerTitle: false,
      ),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Center(
          child: Column(
            children: [
              TextField(
                controller: username,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.person),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Username'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              TextField(
                controller: password,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Password'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              DropdownCrews(),
              // TextField(
              //   controller: crew,
              //   keyboardType: TextInputType.number,
              //   decoration: InputDecoration(
              //     label: Row(
              //       children: const [
              //         Icon(Icons.key),
              //         SizedBox(
              //           width: 10,
              //         ),
              //         Text('Crew'),
              //       ],
              //     ),
              //     fillColor: Colors.grey,
              //   ),
              // ),
              DropdownMajors(),
              // TextField(
              //   controller: major,
              //   keyboardType: TextInputType.number,
              //   decoration: InputDecoration(
              //     label: Row(
              //       children: const [
              //         Icon(Icons.key),
              //         SizedBox(
              //           width: 10,
              //         ),
              //         Text('Major'),
              //       ],
              //     ),
              //     fillColor: Colors.grey,
              //   ),
              // ),
              TextField(
                controller: section,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Section'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              const SizedBox(
                height: 60,
              ),
              MaterialButton(
                  textColor: Colors.white,
                  color: Colors.blue,
                  onPressed: () async {
                    await notifier.createPlayer(
                      username.text,
                      password.text,
                      num.parse(crew.text),
                      num.parse(major.text),
                      num.parse(section.text),
                    );
                    String desc = notifier.createPlayerResponse?.description;
                    if (notifier.createPlayerResponse?.success == true) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Padding(
                            padding: const EdgeInsets.all(10.0),
                            child: Row(
                              children: const [
                                Icon(
                                  Icons.check,
                                  color: Colors.green,
                                  size: 40,
                                ),
                                Spacer(),
                                Flexible(
                                  flex: 6,
                                  child: Text(
                                    'Player Created'
                                    ,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      );
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content:
                                 Text(
                                     'ERROR: $desc'
                                      ),
                        )
                      );
                    }
                  },
                  child: const Text('Create Player')),
            ],
          ),
        ),
      ),
    );
  }
}

/**
 * Dropdown Menu for Crews
 */
class DropdownCrews extends StatefulWidget {
  const DropdownCrews();

  @override
  State <DropdownCrews> createState() => _DropdownCrewsState();
}

class _DropdownCrewsState extends State<DropdownCrews> {

  // String dropDownMajors = 'Major';

  // var majors = ['Software Engineering', 'Psychology', 'Education', 'Business'];
  var crews = ['0: Crews', '1: Null-Pointer', '2: Off-By-One', '3: Out-of-Bounds'];
  String dropDownCrew = '0: Crews';

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
        value: dropDownCrew,
        icon: const Icon(Icons.keyboard_arrow_down),
        items: crews.map((String crews) {
          return DropdownMenuItem(
            value: crews,
            child: Text(crews),
          );
        }).toList(),

        onChanged: (String? newValue) {
          setState(() {
            dropDownCrew = newValue!;
          });
        }
    );
  }
}

/**
 * Dropdown Menu for Majors
 */
class DropdownMajors extends StatefulWidget {
  const DropdownMajors();

  @override
  State <DropdownMajors> createState() => _DropdownMajorsState();
}

class _DropdownMajorsState extends State<DropdownMajors> {

  String dropdownMajors = '0: Major';

  var majors = ['0: Major', '1: Software Engineering', '2: Psychology', '3: Education', '4: Business'];

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
        value: dropdownMajors,
        icon: const Icon(Icons.keyboard_arrow_down),
        items: majors.map((String majors) {
          return DropdownMenuItem(
            value: majors,
            child: Text(majors),
          );
        }).toList(),

        onChanged: (String? newValue) {
          setState(() {
            dropdownMajors = newValue!;
          });
        }
    );
  }
}

