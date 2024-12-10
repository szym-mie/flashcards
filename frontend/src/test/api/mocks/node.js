import { setupServer } from 'msw/node';
import { flashcardControllerHandlers } from './handlers';

export const flashcardControllerServer = (host) => setupServer(...flashcardControllerHandlers(host));