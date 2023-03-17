import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/change_password/bloc/change_password_bloc.dart';

import 'package:game_manager/repository/player/player_repository.dart';
import '../../repository/player/basic_response.dart';

class ChangePasswordPage extends StatefulWidget {
  const ChangePasswordPage({Key? key}) : super(key: key);

  @override
  State<ChangePasswordPage> createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {
  final username = TextEditingController();
  final passwordFirst = TextEditingController();
  final passwordConfirm = TextEditingController();
  bool passwordVisible = false;
  bool isMatching = true;
  bool isSecure = true;

  @override
  void initState() {
    super.initState();
    passwordFirst.addListener(_checkSecure);
    passwordFirst.addListener(_checkMatch);
    passwordConfirm.addListener(_checkMatch);
  }

  @override
  dispose() {
    super.dispose();
    passwordFirst.removeListener(_checkSecure);
    passwordFirst.removeListener(_checkMatch);
    passwordConfirm.removeListener(_checkMatch);
    passwordFirst.dispose();
    passwordConfirm.dispose();
  }

  void _checkSecure() {
    setState(() {
      /**
       * Password Requirements
       * 8-16 characters in length
       * 1+ capital letters
       * 1+ lowercase letters
       * 1+ special characters
       */
      isSecure = true;

      // 8 - 16 char length
      if(passwordFirst.text.length < 8 || passwordFirst.text.length > 16)
      {
        isSecure = false;
      }

      final capsRegex = RegExp(r'[A-Z]');
      final lowerRegex = RegExp(r'[a-z]');
      final specialRegex = RegExp(r'[^a-zA-Z]');

      if(!capsRegex.hasMatch(passwordFirst.text) ||
          !lowerRegex.hasMatch(passwordFirst.text) ||
          !specialRegex.hasMatch(passwordFirst.text))
      {
        isSecure = false;
      }
    });
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
                listener: (context, state) {},
                builder: (context, state) {
                  if (state is ChangePasswordComplete) {
                    return buildRequestCompleteScreen(state.response);
                  } else {
                    return buildInputScreen();
                  }
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
            decoration: const InputDecoration(
              label: Row(
                children: [
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
          // TextField to enter new password
          TextField(
            controller: passwordFirst,
            obscureText: !passwordVisible,
            decoration: InputDecoration(
              label: const Row(
                children: [
                  Icon(Icons.key),
                  SizedBox(
                    width: 10,
                  ),
                  Text('New Password'),
                ],
              ),
              errorText: isSecure ? null : "Password Requirements:\n"
                  "8-16 characters in length\n"
                  "1+ capital letters\n"
                  "1+ lowercase letters\n"
                  "1+ special characters\n",
              suffixIcon: IconButton(
                icon: const Icon(Icons.visibility),
                onPressed: () {
                  setState(() {passwordVisible = !passwordVisible;                  });
                },
              ),
              fillColor: Colors.grey,
            ),
          ),
          // TextField to confirm password
          TextField(
            controller: passwordConfirm,
            obscureText: !passwordVisible,
            decoration: InputDecoration(
              label: const Row(
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
          SubmitButtonBuilder(username: username,
              password: passwordConfirm, isValid: (isMatching && isSecure))
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