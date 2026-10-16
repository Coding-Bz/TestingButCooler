from ollama import chat
from ollama import ChatResponse
import sys

a1 = sys.argv[1]
a2 = sys.argv[2]

response: ChatResponse = chat(
    model='gemma3',
    messages=[
        {
            'role': 'user',
            'content': f'Compare "{a1}" and "{a2}". Reply with only -1, 0, or 1.',
        },
    ],
)

print(response.message.content)
