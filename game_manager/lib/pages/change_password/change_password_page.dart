import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/change_password/bloc/change_password_bloc.dart';
import 'package:game_manager/pages/shared/widgets/password.dart';

import 'package:game_manager/repository/player/player_repository.dart';
import '../../repository/player/all_players_response.dart';
import '../../repository/player/basic_response.dart';
import '../../repository/player/player.dart';
import '../shared/widgets/notification_card.dart';

class ChangePasswordPage extends StatefulWidget {
  final PlayerRepository playerRepository;
  const ChangePasswordPage({Key? key, required this.playerRepository}) : super(key: key);

  @override
  State<ChangePasswordPage> createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {
  final passwordFirst = TextEditingController();
  final passwordConfirm = TextEditingController();
  String? username;
  bool isMatching = true;
  PasswordValidator validator = PasswordValidator();
  late PlayerRepository playerRepository;


  @override
  void initState() {
    super.initState();
    passwordFirst.addListener(_checkMatch);
    passwordConfirm.addListener(_checkMatch);
    playerRepository = widget.playerRepository;
  }

  @override
  dispose() {
    super.dispose();
    passwordFirst.removeListener(_checkMatch);
    passwordConfirm.removeListener(_checkMatch);
    passwordFirst.dispose();
    passwordConfirm.dispose();
  }

  // Function to sync the password visibilities by refreshing Parent state
  refresh() {
    setState(() {});
  }

  void _checkMatch() {
    setState(() {
      isMatching = passwordFirst.text == passwordConfirm.text;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: const Text('Change Password'),
        ),
        body: RepositoryProvider(
            create: (context) => playerRepository,
            child: BlocProvider<ChangePasswordBloc>(
                create: (context) => ChangePasswordBloc(
                    playerRepository: context.read<PlayerRepository>())..add(
                        GetPlayerNamesForPageEvent()),
                child: BlocConsumer<ChangePasswordBloc, ChangePasswordState>(
                    listener: (context, state) {
                  if (state is ChangePasswordComplete) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                          content: NotificationCard(
                              cardTitle:
                                  state.response.success ? "Success" : "Error",
                              description: state.response.description,
                              success: state.response.success),
                          duration: const Duration(seconds: 3),
                          behavior: SnackBarBehavior.floating,
                          padding: EdgeInsets.zero),
                    );
                  }
                }, builder: (context, state) {
                      return BlocBuilder<ChangePasswordBloc, ChangePasswordState>(builder: (context, state) {
                        if (state is PasswordPageReady) {
                          return buildInputScreen(state.response);
                        } else {
                          return const Center(child: CircularProgressIndicator());
                        }
                      });
                }))));
  }

  Widget buildInputScreen(AllPlayersResponse playersResponse) => Padding(
      padding: const EdgeInsets.all(24.0),
      child: Center(
          child: Column(children: [
        DropdownButtonFormField(
          decoration: const InputDecoration(
              prefixIcon: Icon(Icons.person, color: Colors.black)),
          hint: const Text("Players"),
          value: username,
          onChanged: (String? value) {
            setState(() {
              username = value!;
            });
          },
          items: playersResponse.players
              .map<DropdownMenuItem<String>>((Player player) {
            return DropdownMenuItem<String>(
              value: player.playerName,
              child: Text(player.playerName),
            );
          }).toList(),
        ),
        // Special TextField to enter new password
        PasswordField(
          controller: passwordFirst,
          validator: validator,
          notifyParent: refresh,
        ),
        // TextField to confirm password
        TextField(
          controller: passwordConfirm,
          obscureText: !validator.passwordVisible,
          decoration: InputDecoration(
            label: Row(
              children: [
                Icon(Icons.key),
                SizedBox(
                  width: 10,
                ),
                Text('Confirm Password'),
              ],
            ),
            errorText: isMatching ? null : "The two passwords do not match.",
            fillColor: Colors.grey,
          ),
        ),
        const SizedBox(
          height: 60,
        ),
        SubmitButtonBuilder(
            username: username ?? "",
            password: passwordConfirm,
            isValid: (isMatching && validator.isSecure && username != null))
      ])));

  Widget buildRequestCompleteScreen(BasicResponse data) =>
      Center(child: Text('$data'));
}

class SubmitButtonBuilder extends StatelessWidget {
  const SubmitButtonBuilder(
      {Key? key,
      required this.username,
      required this.password,
      required this.isValid})
      : super(key: key);

  final String username;
  final TextEditingController password;
  final bool isValid;

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
            : () => BlocProvider.of<ChangePasswordBloc>(context)
                .add(SendChangePasswordEvent(username, password.text)),
        child: const Text(
          "Change Password",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
