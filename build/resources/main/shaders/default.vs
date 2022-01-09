#version 330 core

uniform mat4 matProjection;

in vec4 inPosition;
in vec3 inColor;

out vec4 outColor;

void main(void)
{             
    outColor = vec4(inColor, 1.0f);
    gl_Position = matProjection * inPosition;
}