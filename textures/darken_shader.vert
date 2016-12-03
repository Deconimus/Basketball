#version 120

uniform float uSubs;

attribute vec4 Color;
attribute vec2 TexCoord;

varying vec4 vColor;
varying vec2 vTexCoord;
varying float vSubs;

void main() {
	vColor = Color;
	vTexCoord = gl_MultiTexCoord0.xy;
	vSubs = uSubs;
   	gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
   	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}