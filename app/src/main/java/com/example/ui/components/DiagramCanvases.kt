package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.*

@Composable
fun CatenaryProfileCanvas(
    spanLength: Double,
    sag: Double,
    supportHeight: Double,
    minClearance: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        val cyanColor = HighVoltageCyan
        val outlineColor = MaterialTheme.colorScheme.outline

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val groundY = h - 20f
            val towerLeftX = 40f
            val towerRightX = w - 40f

            // Ground Line
            drawLine(
                color = Color.Gray,
                start = Offset(0f, groundY),
                end = Offset(w, groundY),
                strokeWidth = 3f
            )

            // Tower Supports
            val towerHeightPx = (h - 50f)
            val attachY = groundY - towerHeightPx

            // Left tower
            drawLine(
                color = outlineColor,
                start = Offset(towerLeftX, groundY),
                end = Offset(towerLeftX, attachY),
                strokeWidth = 4f
            )
            // Left crossarm
            drawLine(
                color = outlineColor,
                start = Offset(towerLeftX - 15f, attachY),
                end = Offset(towerLeftX + 15f, attachY),
                strokeWidth = 3f
            )

            // Right tower
            drawLine(
                color = outlineColor,
                start = Offset(towerRightX, groundY),
                end = Offset(towerRightX, attachY),
                strokeWidth = 4f
            )
            // Right crossarm
            drawLine(
                color = outlineColor,
                start = Offset(towerRightX - 15f, attachY),
                end = Offset(towerRightX + 15f, attachY),
                strokeWidth = 3f
            )

            // Conductor Catenary Curve
            val path = Path()
            val steps = 30
            val midX = (towerLeftX + towerRightX) / 2f
            val sagPx = (towerHeightPx * 0.55f).coerceAtMost(towerHeightPx - 25f)
            val midY = attachY + sagPx

            for (i in 0..steps) {
                val t = i.toFloat() / steps.toFloat()
                val px = towerLeftX + (towerRightX - towerLeftX) * t
                // Parabolic approximation for drawing
                val normX = (px - midX) / ((towerRightX - towerLeftX) / 2f)
                val py = midY - sagPx * (1f - normX * normX)

                if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
            }

            drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )

            // Midspan Sag Dimension Line
            drawLine(
                color = cyanColor,
                start = Offset(midX, attachY),
                end = Offset(midX, midY),
                strokeWidth = 2f
            )

            // Ground Clearance Line
            drawLine(
                color = SafetyGreen,
                start = Offset(midX, midY),
                end = Offset(midX, groundY),
                strokeWidth = 2f
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Span: ${spanLength.toInt()} ft", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Sag: ${String.format("%.1f", sag)} ft", style = MaterialTheme.typography.labelSmall, color = HighVoltageCyan)
            Text("Clearance: ${String.format("%.1f", minClearance)} ft", style = MaterialTheme.typography.labelSmall, color = SafetyGreen)
        }
    }
}

@Composable
fun BlowoutArcCanvas(
    blowoutAngleDeg: Double,
    displacementFt: Double,
    windForce: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        val cyanColor = HighVoltageCyan
        val outlineColor = MaterialTheme.colorScheme.outline

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val pivotX = w * 0.35f
            val pivotY = 25f
            val pendulumLen = h - 60f

            // Tower Crossarm
            drawLine(
                color = outlineColor,
                start = Offset(pivotX - 50f, pivotY),
                end = Offset(pivotX + 50f, pivotY),
                strokeWidth = 4f
            )

            // Vertical Reference (Plumb line)
            drawLine(
                color = Color.Gray,
                start = Offset(pivotX, pivotY),
                end = Offset(pivotX, pivotY + pendulumLen),
                strokeWidth = 2f
            )

            // Deflected Conductor (Blowout angle)
            val angleRad = Math.toRadians(blowoutAngleDeg.coerceIn(0.0, 75.0)).toFloat()
            val conductorX = pivotX + (pendulumLen * sin(angleRad))
            val conductorY = pivotY + (pendulumLen * cos(angleRad))

            drawLine(
                color = primaryColor,
                start = Offset(pivotX, pivotY),
                end = Offset(conductorX, conductorY),
                strokeWidth = 4f
            )

            // Conductor circle
            drawCircle(
                color = EpriAmber,
                radius = 8f,
                center = Offset(conductorX, conductorY)
            )

            // Wind arrow
            val windY = pivotY + pendulumLen * 0.5f
            drawLine(
                color = cyanColor,
                start = Offset(pivotX - 60f, windY),
                end = Offset(pivotX + 60f, windY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )

            // ROW Edge Boundary
            val rowEdgeX = w - 30f
            drawLine(
                color = SafetyRed,
                start = Offset(rowEdgeX, 10f),
                end = Offset(rowEdgeX, h - 10f),
                strokeWidth = 2f
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Wind Load: ${String.format("%.2f", windForce)} lb/ft", style = MaterialTheme.typography.labelSmall, color = HighVoltageCyan)
            Text("Blowout: ${String.format("%.1f", blowoutAngleDeg)}°", style = MaterialTheme.typography.labelSmall, color = EpriAmber)
            Text("Displacement: ${String.format("%.1f", displacementFt)} ft", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun GroundingTowerCanvas(
    rodsCount: Int,
    lowFreqOhms: Double,
    impulseOhms: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        val cyanColor = HighVoltageCyan
        val outlineColor = MaterialTheme.colorScheme.outline

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val groundLevelY = h * 0.45f
            val towerCenterX = w * 0.5f

            // Sky / Ground Division
            drawLine(
                color = Color.DarkGray,
                start = Offset(0f, groundLevelY),
                end = Offset(w, groundLevelY),
                strokeWidth = 3f
            )

            // Tower Lattice Legs
            drawLine(
                color = outlineColor,
                start = Offset(towerCenterX - 50f, 15f),
                end = Offset(towerCenterX - 70f, groundLevelY),
                strokeWidth = 3f
            )
            drawLine(
                color = outlineColor,
                start = Offset(towerCenterX + 50f, 15f),
                end = Offset(towerCenterX + 70f, groundLevelY),
                strokeWidth = 3f
            )
            // Cross bracing
            drawLine(
                color = outlineColor,
                start = Offset(towerCenterX - 60f, 20f),
                end = Offset(towerCenterX + 60f, groundLevelY - 15f),
                strokeWidth = 2f
            )

            // Tower Footing Grillage / Base Plates
            drawRect(
                color = Color.Gray,
                topLeft = Offset(towerCenterX - 85f, groundLevelY - 4f),
                size = androidx.compose.ui.geometry.Size(30f, 8f)
            )
            drawRect(
                color = Color.Gray,
                topLeft = Offset(towerCenterX + 55f, groundLevelY - 4f),
                size = androidx.compose.ui.geometry.Size(30f, 8f)
            )

            // Driven Ground Rods
            val rodSpacing = (w - 80f) / (rodsCount + 1).coerceAtLeast(2)
            for (i in 1..rodsCount.coerceAtMost(6)) {
                val rx = 40f + rodSpacing * i
                // Rod
                drawLine(
                    color = primaryColor,
                    start = Offset(rx, groundLevelY),
                    end = Offset(rx, h - 20f),
                    strokeWidth = 4f
                )
            }

            // Buried Counterpoise Wire
            drawLine(
                color = cyanColor,
                start = Offset(20f, groundLevelY + 25f),
                end = Offset(w - 20f, groundLevelY + 25f),
                strokeWidth = 3f
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Rods: $rodsCount driven", style = MaterialTheme.typography.labelSmall, color = EpriAmber)
            Text("60Hz R: ${String.format("%.1f", lowFreqOhms)} Ω", style = MaterialTheme.typography.labelSmall, color = if (lowFreqOhms <= 10.0) SafetyGreen else SafetyYellow)
            Text("Impulse Ri: ${String.format("%.1f", impulseOhms)} Ω", style = MaterialTheme.typography.labelSmall, color = HighVoltageCyan)
        }
    }
}
