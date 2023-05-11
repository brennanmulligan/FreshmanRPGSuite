import 'package:flutter/material.dart';

class NotificationCard extends StatelessWidget {
  final String cardTitle;
  final String description;
  final bool success;
  final Color? color;
  final Icon? icon;

  const NotificationCard({
    Key? key,
    required this.cardTitle,
    required this.description,
    this.success = false,
    this.color,
    this.icon,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: color ?? (success ? Colors.green : Colors.red),
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
              icon ??
                  (success
                      ? const Icon(Icons.check)
                      : const Icon(Icons.warning)),
              const SizedBox(
                width: 12,
              ),
              Text(cardTitle,
                  style: const TextStyle(
                      fontWeight: FontWeight.bold, fontSize: 16)),
            ],
          ),
          const SizedBox(
            height: 12,
          ),
          Row(children: [
            Text(
              description,
              textAlign: TextAlign.left,
            ),
          ])
        ]),
      ),
    );
  }
}
