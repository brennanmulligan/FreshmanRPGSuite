import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:frpg_companion/features/login/login_provider.dart';
import 'package:frpg_companion/features/network/network.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../../objective/objective.dart';

class LoginView extends HookConsumerWidget {
  const LoginView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final username = TextEditingController();
    final password = TextEditingController();

    final notifier = ref.watch(
      LoginProvider.loginController.notifier,
    );

    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text('FreshmanRPG Companion App'),
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
                obscureText: true,
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
              const SizedBox(
                height: 60,
              ),
              MaterialButton(
                textColor: Colors.white,
                color: Colors.blue,
                onPressed: () async {
                  await notifier.loginWithCredentials(
                    username.text,
                    password.text,
                  );

                  debugPrint('Player ID: ${notifier.playerID}');
                  debugPrint('invalid cred: ${notifier.userHasInvalidCred}');

                  if (!notifier.userHasInvalidCred) {
                    final objective = ref.watch(
                      ObjectiveProvider.objectiveController.notifier,
                    );

                    objective.setPlayer(
                      playerName: username.text,
                      playerID: notifier.playerID,
                    );

                    await objective.fetchAllObjective(
                      playerId: notifier.playerID,
                    );

                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const DashboardView(),
                      ),
                    );
                  } else {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                        content: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Row(
                            children: const [
                              Icon(
                                Icons.error,
                                color: Colors.red,
                                size: 40,
                              ),
                              Spacer(),
                              Flexible(
                                flex: 6,
                                child: Text(
                                  'The provided username or password is invalid. Please try again.',
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    );
                  }
                },
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.all(6.0),
                        child: Row(
                          children: const [
                            Icon(
                              Icons.login,
                              semanticLabel: 'Login',
                            ),
                            SizedBox(
                              width: 30,
                            ),
                            Text(
                              'Login',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              const Spacer(),
              Row(
                children: [
                  const Spacer(),
                  Padding(
                    padding: const EdgeInsets.only(
                      bottom: 10,
                    ),
                    child: IconButton(
                      tooltip: 'About this app.',
                      onPressed: () {
                        showAboutDialog(
                          context: context,
                          applicationIcon: const FlutterLogo(),
                          applicationName: 'FreshmanRPG Companion App',
                          applicationVersion: '1.0.0',
                        );
                      },
                      icon: const Icon(Icons.info),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
