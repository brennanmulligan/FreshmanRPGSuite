import 'package:flutter/material.dart';

class NavigationCard extends StatelessWidget {
  final String cardTitle;
  final Icon cardIcon;
  final Widget cardLink;

  const NavigationCard({
    Key? key,
    required this.cardTitle,
    required this.cardIcon,
    required this.cardLink,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => cardLink,
          ),
        );
      },
      child: Padding(
        padding: const EdgeInsets.symmetric(
          vertical: 10,
          horizontal: 12,
        ),
        child: Row(
          children: [
            cardIcon,
            const SizedBox(
              width: 12,
            ),
            Text(cardTitle),
            const Spacer(),
            const Icon(Icons.chevron_right_outlined),
          ],
        ),
      ),
    );
  }
}
