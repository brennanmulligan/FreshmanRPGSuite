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
              TextField(
                controller: crew,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Crew'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              TextField(
                controller: major,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Major'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
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
