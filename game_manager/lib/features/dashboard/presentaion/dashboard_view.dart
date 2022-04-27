import 'package:flutter/material.dart';
import 'package:game_manager/features/dashboard/widgets/navigation_card.dart';
import 'package:game_manager/features/player/presentation/create_player_view.dart';
import 'package:game_manager/features/qr/presentation/create_qr_code_view.dart';

class DashboardView extends StatelessWidget {
  const DashboardView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(12),
        child: ListView(
          children: const [
            NavigationCard(
              cardTitle: 'Create QR',
              cardIcon: Icon(Icons.qr_code),
              cardLink: CreateQRView(),
            ),
            NavigationCard(
              cardTitle: 'Create Player',
              cardIcon: Icon(Icons.accessibility_new_rounded),
              cardLink: CreatePLayerView(),
            ),
          ],
          shrinkWrap: true,
        ),
      ),
    );
  }
}
