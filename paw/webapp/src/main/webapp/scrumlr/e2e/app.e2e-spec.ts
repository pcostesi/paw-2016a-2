import { ScrumlrPage } from './app.po';

describe('scrumlr App', () => {
  let page: ScrumlrPage;

  beforeEach(() => {
    page = new ScrumlrPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
