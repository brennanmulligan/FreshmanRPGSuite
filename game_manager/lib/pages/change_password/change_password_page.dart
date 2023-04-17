import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/change_password/bloc/change_password_bloc.dart';
import 'package:game_manager/pages/shared/widgets/password.dart';

import 'package:game_manager/repository/player/player_repository.dart';
import '../../repository/player/basic_response.dart';
import '../shared/widgets/notification_card.dart';

class ChangePasswordPage extends StatefulWidget {
  const ChangePasswordPage({Key? key}) : super(key: key);

  @override
  State<ChangePasswordPage> createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {
  final username = TextEditingController();
  final passwordFirst = TextEditingController();
  final passwordConfirm = TextEditingController();
  bool isMatching = true;
  PasswordValidator validator = PasswordValidator();

  @override
  void initState() {
    super.initState();
    passwordFirst.addListener(_checkMatch);
    passwordConfirm.addListener(_checkMatch);
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
      body:
      RepositoryProvider(
        create: (context) => PlayerRepository(dio: Dio()),

        child: BlocProvider<ChangePasswordBloc>(
          create: (context) => ChangePasswordBloc
            (playerRepository: context.read<PlayerRepository>()),

            child:
            BlocConsumer<ChangePasswordBloc, ChangePasswordState>(
                listener: (context, state) {
                  if (state is ChangePasswordComplete) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                          content: NotificationCard(
                              cardTitle: state.response.success ? "Success" : "Error",
                              description: state.response.description,
                              success: state.response.success
                          ),
                          duration: const Duration(seconds: 3),
                          behavior: SnackBarBehavior.floating,
                          padding: EdgeInsets.zero
                      ),
                    );
                  }
                },
                builder: (context, state) {
                    return buildInputScreen();
                })
      )));
  }

  Widget buildInputScreen() => Padding(
    padding: const EdgeInsets.all(24.0),
    child: Center(
      child: Column(
        children: [
          // TextField for Players
          TextField(
            controller: username,
            decoration: InputDecoration(
              label: Row(
                children: const [
                  Icon(Icons.person),
                  SizedBox(
                    width: 10,
                  ),
                  Text('Player Username'),
                ],
              ),
              fillColor: Colors.grey,
            ),
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
                children: const [
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
          SubmitButtonBuilder(username: username,
              password: passwordConfirm, isValid: (isMatching && validator.isSecure))
        ]
      )
    )
  );

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

  final TextEditingController username;
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
        onPressed: !isValid ? null : () => BlocProvider.of<ChangePasswordBloc>(context)
            .add(SendChangePasswordEvent(username.text, password.text)),
        child: const Text(
          "Change Password",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}