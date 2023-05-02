import 'package:flutter/material.dart';

class PasswordValidator {
  bool passwordVisible = false;
  bool isSecure = true;
}

class PasswordField extends StatefulWidget {
  final TextEditingController controller;
  final PasswordValidator validator;
  final Function() notifyParent;
  final String requirements = "Password Requirements:\n"
      "8-16 characters in length\n"
      "1+ capital letters\n"
      "1+ lowercase letters\n"
      "1+ special characters\n";

  PasswordField({
    Key? key,
    required this.controller,
    required this.validator,
    required this.notifyParent,
  }) : super(key: key);

  @override
  _PasswordState createState() => new _PasswordState();

}

class _PasswordState extends State<PasswordField> {

  void _checkSecure() {
    setState(() {
      /**
       * Password Requirements
       * 8-16 characters in length
       * 1+ capital letters
       * 1+ lowercase letters
       * 1+ special characters
       */
      widget.validator.isSecure = true;

      // 8 - 16 char length
      if(widget.controller.text.length < 8 || widget.controller.text.length > 16)
      {
        widget.validator.isSecure = false;
      }

      final capsRegex = RegExp(r'[A-Z]');
      final lowerRegex = RegExp(r'[a-z]');
      final specialRegex = RegExp(r'[^a-zA-Z]');

      if(!capsRegex.hasMatch(widget.controller.text) ||
          !lowerRegex.hasMatch(widget.controller.text) ||
          !specialRegex.hasMatch(widget.controller.text))
      {
        widget.validator.isSecure = false;
      }

      widget.notifyParent();
    });
  }

  @override
  void initState() {
    super.initState();
    widget.controller.addListener(_checkSecure);
  }

  @override
  void dispose(){
    super.dispose();
    widget.controller.removeListener(_checkSecure);
  }

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: widget.controller,
      obscureText: !widget.validator.passwordVisible,
      decoration: InputDecoration(
        label: Row(
          children: [
            Icon(Icons.key),
            SizedBox(
              width: 10,
            ),
            Text('New Password'),
          ],
        ),
        errorText: widget.validator.isSecure ? null : widget.requirements,
        suffixIcon: IconButton(
          icon: const Icon(Icons.visibility),
          onPressed: () {
            setState(() {
              widget.validator.passwordVisible = !widget.validator.passwordVisible;
              widget.notifyParent();
            });
          },
        ),
        fillColor: Colors.grey,
      ),
    );
  }
}
