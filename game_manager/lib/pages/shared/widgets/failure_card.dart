import 'package:flutter/material.dart';

class FailureCard extends StatelessWidget {
  final String cardTitle;
  final String errorDescription;

  const FailureCard({
    Key? key,
    required this.cardTitle,
    required this.errorDescription,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.red,
        borderRadius: BorderRadius.circular(10),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(
          vertical: 10,
          horizontal: 12,
        ),
        child: Column(children: [
          Row(
            children: [
              const Icon(Icons.warning),
              const SizedBox(
                width: 12,
              ),
              Text(cardTitle),
            ],
          ),
          const SizedBox(
            height: 12,
          ),
          Row(children: [
            Text(
              errorDescription,
              textAlign: TextAlign.left,
            ),
          ])
        ]),
      ),
    );
  }
}
