#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 textureCoord;

out vec2 texCoord;
out vec4 gl_Position;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main(){

	gl_Position = worldMatrix * projectionMatrix * vec4(vertexPosition, 1);
	texCoord = textureCoord;
}
